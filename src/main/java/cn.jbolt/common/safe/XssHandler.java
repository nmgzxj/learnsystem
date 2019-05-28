package cn.jbolt.common.safe;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class XssHandler extends Handler {

 
	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
 
		//带.表示非action请求
        if (target.indexOf(".") == -1 ){
            request = new HttpServletRequestWrapper(request);
        }
        next.handle(target, request, response, isHandled);
        
	}
 
	 
 
}
