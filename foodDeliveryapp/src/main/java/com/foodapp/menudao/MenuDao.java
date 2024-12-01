package com.foodapp.menudao;

import java.util.List;

import com.foodapp.menu.Menu;

public interface MenuDao {
	public void insert(Menu m);
	   public List<Menu> fetchAll();
	   public Menu fetchspecific(int i);
	   public void update(Menu m);
	   public int delete(int i);
	   public List<Menu> fetchonrid(int id);
}
