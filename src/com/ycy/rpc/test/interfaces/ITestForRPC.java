package com.ycy.rpc.test.interfaces;

import com.ycy.rpc.test.model.UserModel;

import java.util.List;

public interface ITestForRPC {
	UserModel getUserById(String id);
	UserModel getUserById();
	List<UserModel> getUserList();
}
