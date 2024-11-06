package com.soft.base.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "permit")
public class JwtIgnoreProperty {

    List<String> urls;

    public String[] toArray() {
        String[] urls = new String[this.urls.size()];
        for (int i = 0; i < this.urls.size(); i++) {
            urls[i] = this.urls.get(i);
        }
        return urls;
    }
}
