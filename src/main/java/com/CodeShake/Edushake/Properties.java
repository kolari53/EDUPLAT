package com.CodeShake.Edushake;

public enum Properties {
    TOKEN("eyJhbGciOiJSUzI1NiIsImtpZCI6IjQ5OGY2YmY0ZGU2ODBkODY5MWMzZTk3NGZkOTUwMmQ2YjJhNjdiNTUifQ.eyJpc3MiOiJqd3Qtc2lnbmVyQGNvZGVzaGFrZS1nYXRla2VlcGVyLmlhbS5nc2VydmljZWFjY291bnQuY29tIiwiaWF0IjoxNjY1NDY3OTEzLjk1MywiZXhwIjoxNjY2MzMxOTEzLjk1MywibnMiOiIxNGQ2NTRmNC0yODljLTRjZjYtYTEyMC05MTA3YTA0ZWIyZDciLCJuYW1lIjoiZGVuaXogYmVyayBib3prdXJ0IiwiZW1haWwiOiJkZW5pemJlcmtib3prdXJ0QGdtYWlsLmNvbSIsImF1ZCI6ImlvLmNvZGVzaGFrZS5nYXRla2VlcGVyIn0.h8AJDS_b0Rm3NANC83_kIU0gZfe9bv7NZY6frn3JV7xZJHVeAPfMI0P4PSvRXxAevtTIzGAWgHHYY2aCfQQXmzl_z602tlDtkFjSQVmwWiMOpB5s8mtwqElYRD8-IUpLMEqyl8LzkS4zkx9Xx_sF9m8Wl9jFfCWYvCzUjik4gT1PSgO3-Rpep1R-cdJdUgq-BhEuoUXkfvCIEcRHjZ0sKqrEh_zpnPFe7j70Ct01hIOlCMau6VRguLREW-5ALdNkocO9Np61YBfgE4NVyzD9ZbLFs7-TlDoFg-0-MueF-MQyMfvSJzkIlo3Y9id3gibtLex5ONqLaQ6YzoITByXhDA"),
    HOSTADDRESS("https://api.gatekeeper.codeshake.dev/api/sync");
    
    private String Properties;

    Properties(String Properties) {
        this.Properties = Properties;
    }
    public String getProperties() {
        return Properties;
    }
}
