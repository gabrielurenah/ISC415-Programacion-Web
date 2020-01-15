import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Scraper {
    public static void main(String[] args) {
        System.out.println("Insert valid URL. (must contain http or https): ");
        Scanner scan = new Scanner(System.in);
        String url, rawDoc = "";
        Connection connection;
        Document parsedDoc = null;

        do {
            url = scan.nextLine();
            if (isValidURL(url)) {
                System.out.println("Valid URL." + "\u2713"+ " Parsing Website...\n");
            } else {
                System.out.println("Invalid URL." + "\u26A0" + " Please enter a valid URL (i.e.: https://google.com)\n");
            }
        } while (!isValidURL(url));
        scan.close();

        try {
            connection = Jsoup.connect(url);
            parsedDoc = connection.get();
            rawDoc = connection.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ///Section A
        int lines = rawDoc.split("\n").length;
        printSection('A', "\uD83E\uDD16" + "Quantity of lines in the file: ", lines);

        //Section B
        Elements pTags = parsedDoc.getElementsByTag("p");
        printSection('B', "\uD83E\uDD16" + "<p> tags found: ", pTags.size());

        //Section C
        int imgCounter = 0;
        for (Element tag: pTags) {
            imgCounter += tag.getElementsByTag("img").size();
        }
        printSection('C', "\uD83E\uDD16" + "<img> tags inside <p> tags: ", imgCounter);

        //Section D
        printSection('D', "\uD83E\uDD16" + "<form> tags in GET: ", getFormFromMethod(parsedDoc, "GET"));
        printSection('D', "\uD83E\uDD16" + "<form> tags in POST: ", getFormFromMethod(parsedDoc, "POST"));

        //Section E
        System.out.println("=========== SECTION E ===========");
        Elements formTags = parsedDoc.getElementsByTag("form");
        ArrayList<Elements> inputs = new ArrayList<>();
        for (Element form : formTags) {
            inputs.add(form.getElementsByTag("input"));
        }
        System.out.println("\uD83E\uDD16" + "<input> tags in each form: ");
        showInputsInEachForm(inputs);

        //Section F
        System.out.println("=========== SECTION F ===========");
        makeRequest(parsedDoc, url);

    }

    private static void printSection(char letter, String title, int amount) {
        System.out.println("=========== SECTION " + letter + " ===========");
        System.out.println(title + amount);
        System.out.println("_________________________________");
        return;
    }


    private static void makeRequest(Document document, String url){
        Elements forms = document.select("form");

        for(Element form : forms) {
            if(form.attr("method").equalsIgnoreCase("post")){
                String urlToPost = formatURLToSend(form, url);
                System.out.println("\uD83E\uDD16" + "url used for the post " + "\uD83D\uDC49" + urlToPost);
                try{
                    Document response = Jsoup.connect(urlToPost)
                            .data("asignatura", "practica1")
                            .header("matricula", "20160217")
                            .post();

                    System.out.println(response);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private static String formatURLToSend(Element form, String url) {
        String formattedURL = url.substring(0, url.substring(8).indexOf("/") + 8) + form.attr("action");

        return form.attr("action").contains("http")
                ? form.attr("action")
                : formattedURL;
    }

    private static int getFormFromMethod(Document document, String method){
        Elements forms = document.select("form");
        int formsFound = 0;
        for(Element form: forms) {
            if(form.attr("method").equalsIgnoreCase(method))
                formsFound++;
        }
        return formsFound;
    }

    private static void showInputsInEachForm(ArrayList<Elements> inputs) {
        int count = 0;
        for (Elements items : inputs) {
            System.out.println("Form #" + count + ": ");
            for (Element item : items) {
                String type = item.attr("type");
                System.out.println("Type: " + type);
            }
            System.out.println("=================================\n");
            count++;
        }
        return;
    }

    private static boolean isValidURL(String url) {
        String[] schemes = {"http", "https"};
        if (url.startsWith(schemes[0]) || url.startsWith(schemes[1])) {
            return true;
        }
        return false;
    }
}
