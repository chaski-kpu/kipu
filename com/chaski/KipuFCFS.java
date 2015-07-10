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
import java.util.Collections;



public class KipuFCFS  {
	static KipuFCFS instance;
	public static final int quantum = 5;
	private KipuThread always;
	
	ArrayList<KipuThread> threads = new ArrayList<KipuThread>();
	
	
	public static KipuFCFS getInstance(int size) {
	      if(instance == null) {
	         instance = new KipuFCFS();
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
		long reload = quantum;
	
		
		
		while(true) {
			Collections.sort(threads);
			
			if (reload > 0) {
				Thread.sleep(reload);
			}
			always.execute();
			if (threads.size() ==0)
				break;
			
			kipuThread = threads.remove(0);

			if (kipuThread == null)
				throw new Exception("Kipu: Unknown thread error");			
		
			kipuThread.execute();
			
			if (kipuThread.time > 0)
				threads.add(0,kipuThread);
			
			//System.out.println("FCFS Reload done");
	 	}
	}
}
