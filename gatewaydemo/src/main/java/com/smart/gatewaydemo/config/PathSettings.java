package com.smart.gatewaydemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
* class provide path related properties and its getter-setter methods
 */

@Configuration
@ConfigurationProperties("gateway.url")
public class PathSettings {

    private String[] publicPath;

    private String[] authenticatedPath;

    private String[] privatePath;

    private String[] logoutPath;

    public String[] getPublicPath() {
        return publicPath;
    }

    public void setPublicPath(String[] publicPath) {
        this.publicPath = publicPath;
    }

    public String[] getPrivatePath() {
        return privatePath;
    }

    public void setPrivatePath(String[] privatePath) {
        this.privatePath = privatePath;
    }

    public String[] getLogoutPath() {
        return logoutPath;
    }

    public void setLogoutPath(String[] logoutPath) {
        this.logoutPath = logoutPath;
    }

    public String[] getAuthenticatedPath() {
        return authenticatedPath;
    }

    public void setAuthenticatedPath(String[] authenticatedPath) {
        this.authenticatedPath = authenticatedPath;
    }
}
