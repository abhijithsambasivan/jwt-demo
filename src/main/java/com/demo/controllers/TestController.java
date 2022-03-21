package com.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	   @GetMapping("/api/test")
	    public ResponseEntity<String> reachSecureEndpoint() {

	        return new ResponseEntity<String>("If your can read this, you have reached a secured endpoint",HttpStatus.OK);
	    }
	
	
	
}
