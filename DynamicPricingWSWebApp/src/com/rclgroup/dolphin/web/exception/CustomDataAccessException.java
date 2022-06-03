/*-----------------------------------------------------------------------------------------------------------  
CustomDataAccessException.java
------------------------------------------------------------------------------------------------------------- 
Copyright RCL Public Co., Ltd. 2007 
-------------------------------------------------------------------------------------------------------------
Author Piyapong Ieumwananonthachai 04/03/08
- Change Log ------------------------------------------------------------------------------------------------  
## DD/MM/YY -User-     -TaskRef-      -Short Description  
-----------------------------------------------------------------------------------------------------------*/

package com.rclgroup.dolphin.web.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.dao.DataAccessException;

import com.rclgroup.dolphin.web.util.RutErrorMessage;


public class CustomDataAccessException extends DataAccessException{
    private String messages;
    private List errorMessageList;
    Throwable exceptionCause = null;
    
    public CustomDataAccessException(String messages) {
        super(messages);
        this.messages = messages;
        createMessageList(messages);
    }
    
    public CustomDataAccessException(String messages, Throwable exception) {
        super(messages, exception);
        this.messages = messages;
        exceptionCause = exception;
        createMessageList(messages);
    }
    
    public void createMessageList(String messages){
        StringTokenizer st = new StringTokenizer(messages,"&");
        errorMessageList = new ArrayList();
        while(st.hasMoreTokens()){
            String errorMessage = st.nextToken();
            if(errorMessage == null || errorMessage.trim().equals("")){
                break;
            }else{
                StringTokenizer subSt = new StringTokenizer(errorMessage,"%");
                String errorCode = subSt.nextToken();
                List argumentList = new ArrayList();
                while (subSt.hasMoreTokens()) {
                    argumentList.add(subSt.nextToken());
                }
                errorMessageList.add(new RutErrorMessage(errorCode,argumentList));
            }
        } 
    }
    
    public void printStackTrace() {
        if (exceptionCause != null) {
            System.err.println("[CustomDataAccessException][printStackTrace]: An exception has been caused by: " + exceptionCause.toString());
            exceptionCause.printStackTrace();
        }
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
    
    public String getMessages() {
        return messages;
    }

    public void setErrorMessageList(List errorMessageList) {
        this.errorMessageList = errorMessageList;
    }

    public List getErrorMessageList() {
        return errorMessageList;
    }
}
