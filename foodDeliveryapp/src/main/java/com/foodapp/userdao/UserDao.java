package com.foodapp.userdao;

import java.util.List;

import com.foodapp.user.User;

public interface UserDao {
    public int insert(User u);
    public List<User> fetchall();
	public User fetchSpecific(String email);

	public void update(User u);
	public int delete(int i);
	
}

