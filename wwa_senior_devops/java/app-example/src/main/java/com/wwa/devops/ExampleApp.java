package com.wwa.devops;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.util.VersionInfoUtils;
import com.wwa.devops.utils.TestUtils;

@RestController
@EnableAutoConfiguration
public class ExampleApp {

    @RequestMapping("/")
    String home() {
        return String.format("Version: %s\nLibrary: %s\nSpring: %s\nAWS SDK: %s",
                this.getClass().getPackage().getImplementationVersion(), TestUtils.test(),
                SpringApplication.class.getPackage().getImplementationVersion(),
                VersionInfoUtils.getVersion());
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExampleApp.class, args);

        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(new URL("http://localhost:1234/").openStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
        }

        System.exit(0);
    }

}