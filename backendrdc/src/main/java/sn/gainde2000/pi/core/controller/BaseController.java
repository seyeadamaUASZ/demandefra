/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author indiaye
 */
@RestController
public class BaseController {
    public ResponseEntity<String> getHeaders(
                Map<String, String> headers) {
        headers.forEach((k, v) -> System.out.println(k+" : "+v));
        return new ResponseEntity<String>("request-id : " 
                + headers.get("request-id"), HttpStatus.OK);
    }
}
