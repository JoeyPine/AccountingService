package com.joeypine.accounting.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joeypine.accounting.exception.BizErrorCode;
import com.joeypine.accounting.exception.ErrorResponse;
import com.joeypine.accounting.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Slf4j
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                //allow them to see the login page ;)
                return true;
            }
        } else {
//            saveRequest(request);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            val errorResponse = ErrorResponse.builder()
                    .code(BizErrorCode.NO_UNAUTHORIZED)
                    .errorType(ServiceException.ErrorType.Client)
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .message("No access for realted url")
                    .build();

            try (val write = response.getWriter()) {
                write.write(new ObjectMapper().writeValueAsString(errorResponse));
            } catch (IOException ex) {
                log.error("The IO exception is throw");
                return false;
            }
            return false;
        }
    }
}

