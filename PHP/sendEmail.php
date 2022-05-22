<?php
use PHPMailer\PHPMailer\PHPMailer;

require 'PHPMailer/PHPMailer.php';
require 'PHPMailer/SMTP.php';

function sendEmail($to, $subject, $body) {
        $mail = new PHPMailer();
        $mail->isSMTP();
        $mail->Host = 'smtp.gmail.com';
        $mail->SMTPAuth = true;
        $mail->Username = 'mobile.jouvefamily.loginapp@gmail.com';
        $mail->Password = 'pZu9*aqiAhEumRkt!Q!S';
        $mail->SMTPSecure = "ssl";
        $mail->Port = 465;
        $mail->setFrom("mobile.jouvefamily.loginapp@gmail.com", "loginApp");
        $mail->addAddress($to);
        $mail->Subject = $subject;
        $mail->Body = $body;
        if(!$mail->send()) {
                echo 'Erreur, message non envoyé.';
                echo 'Mailer Error: ' . $mail->ErrorInfo;
        }
}
?>
