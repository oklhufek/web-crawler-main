package utb.fai;

import java.net.URI;
import java.net.URISyntaxException;

public class URIinfo {

    public URI uri;
    public int depth;

    URIinfo(URI uri, int depth) {
        this.uri = uri;
        this.depth = depth;
    }

    URIinfo(String str, int depth) {
        try {
            this.uri = new URI(str);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.depth = depth;
    }

}
