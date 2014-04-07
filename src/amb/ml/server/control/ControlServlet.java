/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amb.ml.server.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.json.Json;
import javax.json.*;

import amb.ml.utils.Message;

import java.util.HashMap;
import java.util.Map;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;

import java.io.BufferedReader;
import java.io.InputStream;

/**
 *
 * @author marc
 */
public class ControlServlet extends HttpServlet {

    private String greeting = "Hello World";
    private final amb.ml.generic.AMBApp app;

    public ControlServlet() {
        //app = new SimpleControlServer();
        app = new GPControlServer();
    }

    public ControlServlet(String greeting) {
        this.greeting = greeting;
        //app = new SimpleControlServer();
        app = new GPControlServer();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>" + greeting + "</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        BufferedReader in = null;
        int len = req.getContentLength();
        byte[] input = new byte[len];
        Message mes = new Message();
        try {
            InputStream is = req.getInputStream();

            JsonReader rdr = Json.createReader(is);

            JsonObject obj = rdr.readObject();
            System.out.println("Starting reading Json message");
            String t = obj.getString("type");
            mes.type = obj.getString("type");
            JsonArray values = obj.getJsonArray("values");
            double[] d = new double[values.size()];
            JsonObject result;
            System.out.println("type : " + t);
            for (int i = 0; i < values.size(); i++) {
                result = values.getJsonObject(i);
                System.out.println("value : " + result.getJsonNumber("value"));
                d[i] = result.getJsonNumber("value").doubleValue();
            }
            mes.value = d;

            // Streaming model
//            
//            ArrayList<Double> arr = new ArrayList<>();
//            try (ServletInputStream sin = req.getInputStream()) {
//                JsonParser jr = Json.createParser(sin);
//                JsonParser.Event event;
//
//                String inString = new String(input);
//                System.out.println(inString);
//                event = jr.next();
//
//                // Output contents of "address" object
//                while (event != JsonParser.Event.END_OBJECT) {
//                    switch (event) {
//                        case KEY_NAME: {
//                            System.out.println(jr.getString() + " ");
//                            if (jr.getString().equalsIgnoreCase("type")) {
//                                jr.next();
//                                mes.type = jr.getString();
//                                System.out.println(jr.getString());
//                                //System.out.println("Adding " + jr.getString());
//                            }
//                            break;
//                        }
//                        case VALUE_FALSE: {
//                            System.out.println(false);
//                            break;
//                        }
//                        case VALUE_NULL: {
//                            System.out.println("null");
//                            break;
//                        }
//                        case VALUE_NUMBER: {
//                            if (jr.isIntegralNumber()) {
//                                System.out.println(jr.getInt());
//                            } else {
//                                System.out.println(jr.getBigDecimal());
//                                //System.out.println("Adding " + jr.getBigDecimal());
//                                arr.add(jr.getBigDecimal().doubleValue());
//                            }
//                            break;
//                        }
//                        case VALUE_STRING: {
//                            System.out.println(jr.getString());
//                            break;
//                        }
//                        case VALUE_TRUE: {
//                            System.out.println(true);
//                            break;
//                        }
//                        case START_OBJECT: {
//                            System.out.println("{");
//                            break;
//                        }
//                        case END_OBJECT: {
//                            System.out.println("}");
//                            break;
//                        }
//                        default: {
//
//                        }
//                    }
//                    event = jr.next();
//                }
//                Double[] d = new Double[arr.size()];
//                arr.toArray(d);
//                mes.value = ArrayUtils.toPrimitive(d);
//
//            }
            resp.setStatus(HttpServletResponse.SC_OK);
            // Send the message to the handler of the app and get the response message
            // This has to be blocking = no thread or use join()
            Message response = app.messageHandler(mes);
            // set the response code and write the response data
            try (OutputStreamWriter writer = new OutputStreamWriter(resp.getOutputStream())) {
                Map<String, Object> properties = new HashMap<>(1);
                properties.put(JsonGenerator.PRETTY_PRINTING, true);
                JsonGeneratorFactory jgf = Json.createGeneratorFactory(properties);
                try (JsonGenerator jg = jgf.createGenerator(writer)) {
                    double dbl = 0.01d;
                    jg.writeStartObject();
                    jg.write("type", response.type);
                    jg.writeStartArray("values");
                    for (int i = 0; i < response.value.length; i++) {
                        jg.writeStartObject();
                        jg.write("value", response.value[i]);
                        jg.writeEnd();
                    }
                    jg.writeEnd();
                    jg.writeEnd();
                }
            }
        } catch (IOException e) {
            try {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print(e.getMessage());
                resp.getWriter().close();
            } catch (IOException ioe) {
            }
        }

    }

}
