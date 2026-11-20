package com.trek.core.messaging.service;

import com.trek.core.entity.Trekker;
import com.trek.core.messaging.config.MailProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessagingService {

    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    private static final String SITE_URL = "https://treknepalinfo.bitsinetwork.com";

    @Async
    public void sendWelcomeEmail(Trekker trekker) {
        try {
            String subject = "Welcome to Trek Nepal Info!";
            String displayName = trekker.getFullName() != null ? trekker.getFullName() :
                    (trekker.getUsername() != null ? trekker.getUsername() : "Trekker");
            String htmlBody = getWelcomeEmailHtml(displayName);

            sendHtmlEmail(trekker.getEmail(), subject, htmlBody);
        } catch (Exception e) {
            log.error("Async welcome email failed for user {}: {}", trekker.getEmail(), e.getMessage());
        }
    }

    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        String subject = "Reset Your Password - Trek Nepal Info";
        String htmlBody = getPasswordResetEmailHtml(resetLink);

        sendHtmlEmail(toEmail, subject, htmlBody);
    }

    private void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            helper.setFrom(mailProperties.getFrom());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            mailSender.send(message);
            log.info("HTML email successfully sent to: {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send HTML email to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private String getWelcomeEmailHtml(String username) {
        String exploreUrl = SITE_URL + "/treks";
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <style>
                        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f7f6; margin: 0; padding: 0; }
                        .email-container { max-width: 600px; margin: 20px auto; background: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 10px rgba(0,0,0,0.05); }
                        .header { background: linear-gradient(135deg, #0f2027, #203a43, #2c5364); color: #ffffff; padding: 30px; text-align: center; }
                        .header h1 { margin: 0; font-size: 24px; letter-spacing: 1px; }
                        .content { padding: 30px; color: #333333; line-height: 1.6; }
                        .content h2 { color: #203a43; font-size: 20px; margin-top: 0; }
                        .btn { display: inline-block; padding: 12px 25px; margin: 20px 0; background-color: #008080; color: #ffffff !important; text-decoration: none; border-radius: 4px; font-weight: bold; }
                        .footer { background-color: #f8f9fa; padding: 15px; text-align: center; font-size: 12px; color: #777777; border-top: 1px solid #eeeeee; }
                    </style>
                </head>
                <body>
                    <div class="email-container">
                        <div class="header">
                            <h1>Trek Nepal Info</h1>
                        </div>
                        <div class="content">
                            <h2>Welcome aboard, %s! 🏔️</h2>
                            <p>We are thrilled to have you join our community of adventure seekers. Your account has been created successfully, and you are all set to start discovering your next mountain journey with us.</p>
                            <p>Ready to explore the trails? Click below to check out our upcoming expeditions:</p>
                            <a href="%s" class="btn">Explore Treks</a>
                            <p>If you have any questions or need assistance planning your trip, feel free to reply directly to this email or reach out to our support team.</p>
                            <p>Warm regards,<br><strong>The Trek Nepal Info Team</strong></p>
                        </div>
                        <div class="footer">
                            &copy; 2026 Trek Nepal Info. All rights reserved.
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(username, exploreUrl);
    }

    private String getPasswordResetEmailHtml(String resetLink) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <style>
                        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f7f6; margin: 0; padding: 0; }
                        .email-container { max-width: 600px; margin: 20px auto; background: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 10px rgba(0,0,0,0.05); }
                        .header { background: linear-gradient(135deg, #0f2027, #203a43, #2c5364); color: #ffffff; padding: 30px; text-align: center; }
                        .header h1 { margin: 0; font-size: 24px; letter-spacing: 1px; }
                        .content { padding: 30px; color: #333333; line-height: 1.6; }
                        .content h2 { color: #203a43; font-size: 20px; margin-top: 0; }
                        .btn { display: inline-block; padding: 12px 25px; margin: 20px 0; background-color: #059669; color: #ffffff !important; text-decoration: none; border-radius: 6px; font-weight: bold; }
                        .footer { background-color: #f8f9fa; padding: 15px; text-align: center; font-size: 12px; color: #777777; border-top: 1px solid #eeeeee; }
                        .note { font-size: 12px; color: #666666; word-break: break-all; }
                    </style>
                </head>
                <body>
                    <div class="email-container">
                        <div class="header">
                            <h1>Trek Nepal Info</h1>
                        </div>
                        <div class="content">
                            <h2>Password Reset Request</h2>
                            <p>We received a request to reset the password for your Trek Nepal Info account.</p>
                            <p>Click the button below to choose a new password. This link is valid for <strong>15 minutes</strong>.</p>
                            <a href="%s" class="btn">Reset Password</a>
                            <p>If you didn't request a password reset, you can safely ignore this email—your account remains secure.</p>
                            <hr style="border: none; border-top: 1px solid #eeeeee; margin: 20px 0;">
                            <p class="note">If the button doesn't work, copy and paste this link into your browser:<br><a href="%s">%s</a></p>
                            <p>Best regards,<br><strong>The Trek Nepal Info Team</strong></p>
                        </div>
                        <div class="footer">
                            &copy; 2026 Trek Nepal Info. All rights reserved.
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(resetLink, resetLink, resetLink);
    }
}