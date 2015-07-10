package com.chaski;
/*
Project Kipu 
Copyright (C) 2013, 2014, 2015  Michael Dorin

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/


import java.util.ArrayList;



public class KipuRR  {
	static KipuRR instance;
	private KipuThread always;
	public static final int quantum = 5;
	
	ArrayList<KipuThread> threads = new ArrayList<KipuThread>();
	
	
	public static KipuRR getInstance(int size) {
	      if(instance == null) {
	         instance = new KipuRR();
	      }
	      return instance;
	}

	public void insert(KipuThread x) {
		threads.add(x);
	}
	
	public void always(KipuThread y) {
		always = y;
	}
	
	public void kipuMain() throws Exception {
		KipuThread kipuThread = null;
		long reload = 0;
		
		long time = 0; 

		while(true) {
			if (reload > 0) 
				Thread.sleep(reload);
			
			kipuThread = threads.remove(0);
			if (kipuThread == null)
				throw new Exception("Kipu: Unknown thread error");			
			time = time + reload;
			kipuThread.time = time + kipuThread.reload;			
			kipuThread.execute();
			if (kipuThread.reload != 0)
				threads.add(kipuThread);
			
			reload = quantum;
			System.out.println("RR Reload done");
	 	}
	}
}
