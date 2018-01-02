package com.licc.dove.exception;

import com.licc.dove.util.ResponseVo;
import com.licc.dove.util.ResponseVoUtil;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ExceptionTranslator {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseVo loginException(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        logger.error(e.getMessage());
        String exceptionMsg = getException(e);
        return ResponseVoUtil.failResult(exceptionMsg);
    }

    /**
     * 获取错误信息
     * 
     * @param exception
     * @return
     */

    private String getException(Exception exception) {
          if(exception instanceof BindException){
              return  ((BindException) exception).getBindingResult().getFieldError().getDefaultMessage();
          }
            return ExceptionConst.SYS_EXCEPTION;
    }


}
