package edu.robertob.lexicalanalyzerbackend.Controllers;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;

@WebServlet("/graph")
public class GraphServlet extends HttpServlet {

    private static final String INITIAL_NODE = "INICIO";
    private static final String NEW_LINE_NODE = "//n";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestBody = getRequestBody(req);
        Optional<BufferedImage> image = generateIndividualGraph(requestBody);
        if (image.isPresent()) {
            byte[] bytes = toByteArray(image.get(), "png");
            String bytesBase64 = Base64.encodeBase64String(bytes);
            resp.setContentType("text/plain");
            resp.getWriter().write(bytesBase64);
        } else {
            resp.setContentType("text/plain");
            resp.getWriter().write("No image");
        }
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line = reader.readLine();
            sb.append(line);
        }
        return sb.toString();
    }

    public static byte[] toByteArray(BufferedImage bi, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        return baos.toByteArray();
    }

    protected Optional<BufferedImage> generateIndividualGraph(String lexeme) {
        final int size = lexeme.length();

        Graph graph = graph(lexeme + "-lexema-graph")
                .directed()
                .graphAttr().with(Rank.dir(Rank.RankDir.LEFT_TO_RIGHT))
                .nodeAttr().with(Font.name("Arial"))
                .linkAttr().with("class", "link-class");

        if (size == 0) {
            Node start = node(INITIAL_NODE).with(Shape.NONE) ;
            Node initial = node(NEW_LINE_NODE);

            graph = graph.with(start.with(Color.BLACK).link(initial));
            graph = graph.with(initial.with(Color.BLACK, Shape.DOUBLE_CIRCLE).link(initial));

        } else if (size > 1) {
            Node start = node(INITIAL_NODE).with(Shape.NONE) ;
            Node initial = node(String.valueOf(lexeme.charAt(0)));
            Node last = node(String.valueOf(lexeme.charAt(size - 1)));

            // Link start with initial
            graph = graph.with(start.with(Color.BLACK).link(initial));

            for (int i = 1; i < size - 1; i++) {
                Node node = node(String.valueOf(lexeme.charAt(i)));

                // Previous link
                if (i == 1) {
                    graph = graph.with(initial.with(Color.BLACK).link(node));
                } else {
                    Node previousNode = node(String.valueOf(lexeme.charAt(i - 1)));
                    graph = graph.with(previousNode.with(Color.BLACK).link(node));
                }

                // Next link
                if (i == size - 1) {
                    node = node.with(Color.BLACK).link(last);
                } else {
                    Node nextNode = node(String.valueOf(lexeme.charAt(i + 1)));
                    node = node.with(Color.BLACK).link(nextNode);
                }
                graph = graph.with(node);
            }

            graph = graph.with(last.with(Color.GRAY, Shape.DOUBLE_CIRCLE));

        } else {
            Node start = node(INITIAL_NODE).with(Shape.NONE) ;
            Node initial = node(String.valueOf(lexeme.charAt(0)));

            graph = graph.with(start.with(Color.BLACK).link(initial));
            graph = graph.with(initial.with(Color.BLACK, Shape.DOUBLE_CIRCLE).link(initial));
        }
        return Optional.of(Graphviz.fromGraph(graph).height(300).render(Format.PNG).toImage());
    }
}
