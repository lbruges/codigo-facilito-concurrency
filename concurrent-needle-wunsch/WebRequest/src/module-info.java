module WebRequest {
    exports com.codigofacilito.sequence.api.server;
    exports com.codigofacilito.sequence.api.handler;
    requires jdk.httpserver;
    requires java.net.http;
    requires NeedlemanWunschAligner;
    requires CommonProperties;
}