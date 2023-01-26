package com.khilkoleg.redditapp.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;
import lombok.AllArgsConstructor;

/**
 * @author Oleg Khilko
 */

@Service
@AllArgsConstructor
public class MailContentBuilder {
    private final TemplateEngine templateEngine;

    public String build(String message) {
        var context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("mailTemplate", context);
    }
}