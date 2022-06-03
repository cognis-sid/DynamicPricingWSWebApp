package com.rclgroup.dolphin.web.email;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.rclgroup.dolphin.web.common.RrcApplicationContextWS;
import com.rclgroup.dolphin.web.dao.EmailConfigDao;
import com.rclgroup.dolphin.web.model.EmailConfigMod;
/**
 * This class is use for load and set EmailProperties from database.
 * @author Cognis Solution
 */
public class GetMailProperticeServlet extends HttpServlet {
	private static EmailConfigMod model;

	/**
	 * 
	 * default serialUI
	 */
	private static final long serialVersionUID = 1L;
	private final EmailConfigDao dao = (EmailConfigDao) RrcApplicationContextWS.getBean("emailDao");
	private final JavaMailSenderImpl mailSender = (JavaMailSenderImpl) RrcApplicationContextWS.getBean("mailSender");
	private final EZLMail ezLMail = (EZLMail) RrcApplicationContextWS.getBean("mailMail");
	
	@Override
	public void init() throws ServletException {
		/*EmailConfigMod model =  dao.getEmailConfig();
		 mailSender.setHost(model.getHost());
		 mailSender.setUsername(model.getUsername());
		 mailSender.setPassword(model.getPassword());
		 ezLMail.setAdminEmail(model.getAdminEmail());
		 ezLMail.setMailConfig(model);*/
		EmailConfigMod model = new EmailConfigMod();
		model.setHost("10.0.3.131");
		model.setUsername("rclvas");
		model.setPassword("rclvas");
		 ezLMail.setAdminEmail("no-reply@rclgroup.com");
		 
		 ezLMail.setMailConfig(model);
		 System.out.println("GetMailProperticeServlet >init(): "+model);
	}

	
	public static String mail="<!DOCTYPE html>\r\n" + 
			"<html lang=\"en\">\r\n" + 
			"\r\n" + 
			"<head>\r\n" + 
			"    <meta charset=\"UTF-8\">\r\n" + 
			"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + 
			"    <title>Password for Request Quote</title>\r\n" + 
			"</head>\r\n" + 
			"\r\n" + 
			"<body bgcolor=\"#f5f8fa\" style=\"-webkit-text-size-adjust:none;margin:0;padding:0;\">\r\n" + 
			"    <!-- Background -->\r\n" + 
			"    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"\r\n" + 
			"        style=\"border-collapse:collapse;background-color:#f5f8fa;text-align:center;\">\r\n" + 
			"        <tr>\r\n" + 
			"            <td align=\"center\" class=\"framepadding\" style=\"padding-left:30px;padding-right:30px;\">\r\n" + 
			"\r\n" + 
			"                <!--FRAME-->\r\n" + 
			"                <table class=\"frame\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"\r\n" + 
			"                    style=\"border-collapse:collapse;background-color:#f5f8fa;width:600px;min-width:320px;text-align:center;\"\r\n" + 
			"                    align=\"center\">\r\n" + 
			"\r\n" + 
			"                    <tr>\r\n" + 
			"                        <td style=\"font-size:15px;font-family:Arial,sans-serif;line-height:18px;\">\r\n" + 
			"                            <!-- header -->\r\n" + 
			"                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"\r\n" + 
			"                                style=\"border-collapse:collapse;text-align:left;\">\r\n" + 
			"                                <tr>\r\n" + 
			"                                    <td class=\"preblue\"\r\n" + 
			"                                        style=\"font-size:0px;line-height:0px;padding-left:30px;padding-top:8px;padding-bottom:8px;\">\r\n" + 
			"                                    </td>\r\n" + 
			"                                </tr>\r\n" + 
			"\r\n" + 
			"                            </table>\r\n" + 
			"                            <!--preheader -->\r\n" + 
			"                            <!--/preheader -->\r\n" + 
			"\r\n" + 
			"                        </td>\r\n" + 
			"                    </tr>\r\n" + 
			"                    <tr>\r\n" + 
			"                        <td>\r\n" + 
			"                            <!--/header-->\r\n" + 
			"\r\n" + 
			"                        </td>\r\n" + 
			"                    </tr>\r\n" + 
			"                    <tr>\r\n" + 
			"                        <td>\r\n" + 
			"                            <br />\r\n" + 
			"                            <br />\r\n" + 
			"                            <!-- banner -->\r\n" + 
			"                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"\r\n" + 
			"                                style=\"border-collapse:collapse;text-align:center;\">\r\n" + 
			"                                <tr height=\"60\">\r\n" + 
			"\r\n" + 
			"                                    <td style=\"font-size:10px;color:rgb(0, 0, 1);text-align:center;padding:5px;\">\r\n" + 
			"                                        <img style=\"width:150px; height:auto\"\r\n" + 
			"                                            src=\"data:image/png;base64,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\"\r\n" + 
			"                                            alt=\"RCL Group\" />\r\n" + 
			"                                    </td>\r\n" + 
			"                                </tr>\r\n" + 
			"                            </table>\r\n" + 
			"                            <br />\r\n" + 
			"                            <br />\r\n" + 
			"                            <!--/banner-->\r\n" + 
			"\r\n" + 
			"                        </td>\r\n" + 
			"                    </tr>\r\n" + 
			"                    <tr style=\"border-top:5px solid #004b8e;\">\r\n" + 
			"                        <td>\r\n" + 
			"\r\n" + 
			"                            <!-- body -->\r\n" + 
			"                            <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#ffffff\"\r\n" + 
			"                                align=\"left\" style=\"border-collapse:collapse;text-align:left;\">\r\n" + 
			"                                <tr>\r\n" + 
			"                                    <td class=\"bodypadding\"\r\n" + 
			"                                        style=\"padding:30px;font-size:15px;font-family:Arial,sans-serif;line-height:18px;color:#66757f;\">\r\n" + 
			"\r\n" + 
			"                                        <!-- columns -->\r\n" + 
			"                                        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#ffffff\"\r\n" + 
			"                                            align=\"left\" style=\"border-collapse:collapse;text-align:left;\">\r\n" + 
			"                                            <tr>\r\n" + 
			"\r\n" + 
			"                                                <!-- left column -->\r\n" + 
			"                                                <td class=\"stack\" valign=\"top\"\r\n" + 
			"                                                    style=\"font-size:15px;font-family:Arial,sans-serif;line-height:18px;color:#66757f;vertical-align:top;\">\r\n" + 
			"<!-- Your Email Content Starts here -->\r\n" + 
			"\r\n" + 
			"<p class=\"font-lato\" style=\"margin-top: 0;font-style: normal;font-weight: 400;font-family: lato,tahoma,sans-serif;line-height: 24px;margin-bottom: 24px;color: #565656;font-size: 16px\">\r\n" + 
			"\r\n" + 
			"    \r\n" + 
			"      <strong>Password for Request Quote!\r\n" + 
			"      </strong><br /> \r\n" + 
			"       \r\n" + 
			"</p>\r\n" + 
			"\r\n" + 
			"<div>\r\n" + 
			"            <strong style=\"display:inline-block;\" class=\"text-xl\">Hi, </strong> \r\n" + 
			"            \r\n" + 
			"         </div>\r\n" + 
			"<p class=\"font-lato\" style=\"margin-top: 0;font-style: normal;font-weight: 400;font-family: lato,tahoma,sans-serif;line-height: 24px;margin-bottom: 24px;color: #565656;font-size: 16px\">\r\n" + 
			"<div class=\"row\" style=\"line-height: 1.9;\">\r\n" + 
			"                        <div class=\"col-md-3\">\r\n" + 
			"                            \r\n" + 
			"                             \r\n" + 
			"                           <div class=\"d-flex\">Your one time password  is  :<strong>token__id</strong></div>\r\n" + 
			"                            \r\n" + 
			"                        </div>\r\n" + 
			"                        \r\n" + 
			"                     </div>\r\n" + 
			"</p>\r\n" + 
			"<p class=\"font-lato\" style=\"margin-top: 0;font-style: normal;font-weight: 400;font-family: lato,tahoma,sans-serif;line-height: 24px;margin-bottom: 24px;color: #565656;font-size: 16px\">\r\n" + 
			"\r\n" + 
			"    <strong>Your password will be valid for 10 minutes.</strong> <br /> \r\n" + 
			"    \r\n" + 
			"</p>\r\n" + 
			"<hr/>\r\n" + 
			"<p class=\"font-lato\" style=\"margin-top: 0;font-style: normal;font-weight: 400;font-family: lato,tahoma,sans-serif;line-height: 24px;margin-bottom: 24px;color: #565656;font-size: 16px\">\r\n" + 
			"    Note: The carrier reserves its full rights, and at any given time without prior notice, change the arrangement listed or make alternate arrangment or decline a booking.\r\n" + 
			"\r\n" + 
			"    <br/><br/>\r\n" + 
			"\r\n" + 
			"    Please do not reply to this message.<br/>\r\n" + 
			"    2013 RCL Feeder Pte Ltd. | All Rights Reserved.\r\n" + 
			"    <br/><br/>\r\n" + 
			"</p>\r\n" + 
			"<p class=\"font-lato\" style=\"margin-top: 0;font-style: normal;font-weight: 400;font-family: lato,tahoma,sans-serif;line-height: 24px;Margin-bottom: 16px;color: #565656;font-size: 12px\">\r\n" + 
			"    RCL CONFIDENTIALITY AND DISCLAIMER<br/>\r\n" + 
			"    The contents in this email and any attachments are confidential to the intended recipient(s). If you have received it in error, please notify the sender immediately and then delete it. Any other use of this email and any related attachment(s) is prohibited. Please note that neither RCL nor the sender accepts any responsibility for any viruses, and it is your responsibility to scan or otherwise check this email and any attachments.\r\n" + 
			"</p>\r\n" + 
			"\r\n" + 
			"<!-- Your Email Content Ends here -->\r\n" + 
			"                                                    \r\n" + 
			"                                                    <br /><br />\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"                                                    <div>\r\n" + 
			"                                                        <!--[if mso]>\r\n" + 
			"                                                                 <v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"https://twitter.twimg.com/TwitterAcademy\" style=\"height:45px;v-text-anchor:middle;width:200px;\" arcsize=\"12%\" stroke=\"f\" fillcolor=\"#55acee\">\r\n" + 
			"                                                                   <w:anchorlock/>\r\n" + 
			"                                                                   <center>\r\n" + 
			"                                                        <![endif]-->\r\n" + 
			"                                                        <br />\r\n" + 
			"                                                        <br />\r\n" + 
			"\r\n" + 
			"                                                        <!--[if mso]>\r\n" + 
			"                                                            </center>\r\n" + 
			"                                                          </v:roundrect>\r\n" + 
			"                                                        <![endif]-->\r\n" + 
			"                                                    </div>\r\n" + 
			"                                                    </center>\r\n" + 
			"\r\n" + 
			"                                                </td>\r\n" + 
			"                                                <!-- /left column -->\r\n" + 
			"\r\n" + 
			"                                            </tr>\r\n" + 
			"                                        </table>\r\n" + 
			"                                        <!-- /columns -->\r\n" + 
			"\r\n" + 
			"                                    </td>\r\n" + 
			"                                </tr>\r\n" + 
			"                            </table>\r\n" + 
			"                            <!-- /body -->\r\n" + 
			"\r\n" + 
			"                        </td>\r\n" + 
			"                    </tr>\r\n" + 
			"                    <br />\r\n" + 
			"                    <br />\r\n" + 
			"                    <br />\r\n" + 
			"                    <tr>\r\n" + 
			"                        <td>\r\n" + 
			"\r\n" + 
			"                            <!-- footer -->\r\n" + 
			"                            <table width=\"100%\" class=\"footer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\r\n" + 
			"                                bgcolor=\"#ffffff\" align=\"left\" style=\"border-collapse:collapse;text-align:left;\">\r\n" + 
			"                                <tbody>\r\n" + 
			"                                    <tr height=\"1px\" bgcolor=\"eeeeee\">\r\n" + 
			"                                        <td style=\"line-height:1px;font-size:1px;\">&nbsp;</td>\r\n" + 
			"                                    </tr>\r\n" + 
			"                                    <tr>\r\n" + 
			"                                        <td\r\n" + 
			"                                            style=\"padding-top:10px;padding-left:20px;padding-bottom:10px;font-family:sans-serif;font-size:10px;color:rgb(136, 136, 136);line-height:30px;text-align:center\">\r\n" + 
			"\r\n" + 
			"                                            <a style=\"color:rgb(153, 153, 153);text-decoration:none;\"\r\n" + 
			"                                                href=\"https://www.rclgroup.com\">© 2018 RCL| All Rights Reserved.</a>\r\n" + 
			"                                            \r\n" + 
			"\r\n" + 
			"                                        </td>\r\n" + 
			"                                    </tr>\r\n" + 
			"\r\n" + 
			"                                </tbody>\r\n" + 
			"                            </table>\r\n" + 
			"                            <!-- /footer -->\r\n" + 
			"\r\n" + 
			"                        </td>\r\n" + 
			"                    </tr>\r\n" + 
			"                    <tr>\r\n" + 
			"                        <td bgcolor=\"#f5f8fa\">\r\n" + 
			"                            &nbsp;\r\n" + 
			"                        </td>\r\n" + 
			"                    </tr>\r\n" + 
			"\r\n" + 
			"                </table>\r\n" + 
			"                <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"\r\n" + 
			"                    style=\"border-collapse:collapse;width:100%;text-align:center;\">\r\n" + 
			"                    <tr height=\"50\">\r\n" + 
			"                        <td\r\n" + 
			"                            style=\"padding-top:10px;padding-left:20px;padding-bottom:10px;font-family:sans-serif;font-size:10px;color:rgb(136, 136, 136);line-height:30px;text-align:center\">\r\n" + 
			"                            Please do not reply to this email.If you have any questions, contact your admin.\r\n" + 
			"                            <a href=\"mailto:contact@rclgroup.com\"\r\n" + 
			"                                style=\"color:rgb(0, 0, 1);\">Report&nbsp;a&nbsp;problem</a>&nbsp;&nbsp;&nbsp;&nbsp;\r\n" + 
			"                            &nbsp;\r\n" + 
			"                    </tr>\r\n" + 
			"                </table>\r\n" + 
			"            </td>\r\n" + 
			"        </tr>\r\n" + 
			"\r\n" + 
			"    </table>\r\n" + 
			"    <!-- /FRAME -->\r\n" + 
			"    <!-- /Background -->\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"</body>\r\n" + 
			"\r\n" + 
			"</html>";
	
}
