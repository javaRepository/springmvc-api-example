package com.company.example.controller;

import com.alibaba.fastjson.JSON;
import com.company.example.dao.UserDAO;
import com.company.example.exception.BackEndException;
import com.company.example.exception.InnerAccessInterfaceException;
import com.company.example.exception.ParamException;
import com.company.example.model.User;
import com.company.example.outerApi.request.AddUserInfoRequest;
import com.company.example.outerApi.request.GetUserInfoRequest;
import com.company.example.outerApi.response.AddUserInfoResponse;
import com.company.example.outerApi.response.GetUserInfoResponse;
import com.company.example.service.UserService;
import com.company.example.util.RequestIPUtil;
import com.jtool.codegenannotation.CodeGenApi;
import com.jtool.codegenannotation.CodeGenRequest;
import com.jtool.codegenannotation.CodeGenResponse;
import com.jtool.support.log.LogHelper;
import com.jtool.validator.ParamBeanValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class UserController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserDAO userDAO;

    @Resource
    private UserService userService;

    /*
	<logicInfo>
		1:根据用户名，查询用户信息<br/>
	</logicInfo>
	 */

    @CodeGenApi(name = "(内)查询用户信息", docSeq = 1.0, description = "查询用户信息")
    @CodeGenRequest(GetUserInfoRequest.class)
    @CodeGenResponse(GetUserInfoResponse.class)
    @ResponseBody
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getUserInfo(HttpServletRequest request, GetUserInfoRequest getUserInfoRequest)
            throws ParamException, InnerAccessInterfaceException, BackEndException {
        // 判断是内网还是外网访问
        if (!RequestIPUtil.getWhere(request)) {
            throw new InnerAccessInterfaceException();
        }

        log.debug("查询用户信息request：" + JSON.toJSONString(getUserInfoRequest));
        //检查请求参数
        validateRequestParam(getUserInfoRequest);

        String username = getUserInfoRequest.getUsername();
        LogHelper.setLogUserId(username);

        GetUserInfoResponse getUserInfoResponse = new GetUserInfoResponse();
        getUserInfoResponse.setCode("0");

        User user = userService.getUserByRedis(username);
        if (null == user) {
            user = userDAO.getUser(username).get();
        }
        getUserInfoResponse.setUsername(username);
        getUserInfoResponse.setUserpwd(user.getUserpwd());
        getUserInfoResponse.setAddtime(user.getAddtime());

        log.debug("查询用户信息response：" + JSON.toJSONString(getUserInfoResponse));
        return JSON.toJSONString(getUserInfoResponse);
    }

    private void validateRequestParam(GetUserInfoRequest getUserInfoRequest) throws ParamException {
        if (ParamBeanValidator.isNotValid(getUserInfoRequest)) {
            throw new ParamException(getUserInfoRequest.toString());
        }
    }

    @CodeGenApi(name = "(内)添加用户信息", docSeq = 2.0, description = "添加用户信息")
    @CodeGenRequest(AddUserInfoRequest.class)
    @CodeGenResponse(AddUserInfoResponse.class)
    @ResponseBody
    @RequestMapping(value = "/addUserInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String addUserInfo(HttpServletRequest request, AddUserInfoRequest addUserInfoRequest)
            throws ParamException, InnerAccessInterfaceException, BackEndException {
        // 判断是内网还是外网访问
        if (!RequestIPUtil.getWhere(request)) {
            throw new InnerAccessInterfaceException();
        }

        log.debug("添加用户信息request：" + JSON.toJSONString(addUserInfoRequest));
        //检查请求参数
        validateAddUserRequestParam(addUserInfoRequest);

        String username = addUserInfoRequest.getUsername();
        String userpwd = addUserInfoRequest.getUserpwd();
        LogHelper.setLogUserId(username);

        AddUserInfoResponse addUserInfoResponse = new AddUserInfoResponse();
        addUserInfoResponse.setCode("0");

        User user = new User();
        user.setUsername(username);
        user.setUserpwd(userpwd);
        user.setAddtime(new Date());
        userDAO.addUser(user);

        userService.delUserByRedis(username);
        userService.addUserByRedis(user);

        log.debug("添加用户信息response：" + JSON.toJSONString(addUserInfoResponse));
        return JSON.toJSONString(addUserInfoResponse);
    }

    private void validateAddUserRequestParam(AddUserInfoRequest addUserInfoRequest) throws ParamException {
        if (ParamBeanValidator.isNotValid(addUserInfoRequest)) {
            throw new ParamException(addUserInfoRequest.toString());
        }
    }

}
