package com.trek.admin.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class DefaultController {
    @GetMapping
    public ResponseEntity<String> getDefaultPage() {
        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Trekking</title>
                    <style>
                        body {
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            height: 100vh;
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            margin: 0;
                            text-align: center;
                        }
                        .container {
                            background: white;
                            padding: 40px;
                            border-radius: 10px;
                            box-shadow: 0 0 10px rgba(0,0,0,0.1);
                        }
                        h1 {
                            margin: 0;
                            font-size: 2em;
                            color: #333;
                        }
                        p {
                            margin-top: 10px;
                            font-size: 1.2em;
                            color: #666;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>Travel Application is Running</h1>
                    </div>
                </body>
                </html>
                """;

        return ResponseEntity.ok(html);
    }
}

