package org.summarly.lib.common;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.sax.HTMLDocument;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

/**
 * Created by Anton Chernetskij
 */
public class TextReader {

    public String readText(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(path);
            Scanner scanner = new Scanner(fis, "UTF-8");
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }
    
    public String readText(URL url) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(
            		new InputStreamReader(url.openStream(), "UTF-8"));
            Scanner scanner = new Scanner(in);
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine()).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }

    public String readTextFromURL(String url) {
        String res;

        try {
            ArticleExtractor articleExtractor = ArticleExtractor.getInstance();

            res = articleExtractor.getText(fetch(new URL(url)).toInputSource());
        } catch (IOException | BoilerpipeProcessingException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    private static HTMLDocument fetch(final URL url) throws IOException {
        final URLConnection conn = url.openConnection();
        final String charset = conn.getContentEncoding();


        InputStream in = conn.getInputStream();

        final String encoding = conn.getContentEncoding();
        if(encoding != null) {
            if("gzip".equalsIgnoreCase(encoding)) {
                in = new GZIPInputStream(in);
            } else {
                System.err.println("WARN: unsupported Content-Encoding: "+encoding);
            }
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int r;
        while ((r = in.read(buf)) != -1) {
            bos.write(buf, 0, r);
        }
        in.close();

        final byte[] data = bos.toByteArray();

        Charset cs;
        if (charset != null) {
            try {
                cs = Charset.forName(charset);
            } catch (UnsupportedCharsetException e) {
                cs = Charset.forName(guessEncoding(data));
            }
        } else {
            cs = Charset.forName(guessEncoding(data));
        }

        return new HTMLDocument(data, cs);
    }

    public static String guessEncoding(byte[] bytes) {
        String DEFAULT_ENCODING = "UTF-8";
        org.mozilla.universalchardet.UniversalDetector detector =
                new org.mozilla.universalchardet.UniversalDetector(null);
        detector.handleData(bytes, 0, bytes.length);
        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        detector.reset();
        if (encoding == null) {
            encoding = DEFAULT_ENCODING;
        }
        return encoding;
    }
}
