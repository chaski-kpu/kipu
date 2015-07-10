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


public class Kipu  {
	protected KipuThread[] threads;   
	protected int count = 0;        
	private KipuThread always;

	private static Kipu instance = null;
	protected Kipu() {
	}

	protected Kipu(int capacity)  throws IllegalArgumentException {
		if (capacity <= 0) throw new IllegalArgumentException();
		threads = new KipuThread[capacity];
	}

	public static Kipu getInstance(int size) {
	      if(instance == null) {
	         instance = new Kipu(size);
	      }
	      return instance;
	}

	
	protected final int parent(int k) { return (k - 1) / 2;  }
	protected final int left(int k)   { return 2 * k + 1; }
	protected final int right(int k)  { return 2 * (k + 1); }

	public void insert(KipuThread x) {

		int k = count;
		count++;

		while (k > 0) {
			int par = parent(k);
			 if (x.time < threads[par].time) {
				threads[k] = threads[par];
				k = par;
			} 
			else break;
		}
		threads[k] = x;
	}


	public KipuThread extract() {
		if (count < 1) return null;
		int k = 0;  
		KipuThread least = threads[k];
		count--;
		KipuThread x = threads[count];
		threads[count] = null;
		for (;;) {
			int l = left(k);
			if (l >= count)
				break;
			else {
				int r = right(k);
				int child = (r >= count || threads[l].time < threads[r].time )? l : r; 
				if (x.time > threads[child].time) {
					threads[k] = threads[child];
					k = child;
				}
				else break;
			}
		}
		threads[k] = x;
		return least;
	}

	public KipuThread peek() {
		if (count > 0) 
			return threads[0];
		else
			return null;
	}

	public int size() {
		return count;
	}

	public void clear() {
		for (int i = 0; i < count; ++i)
			threads[i] = null;
		count = 0;
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
			
			kipuThread = this.extract();
			if (kipuThread == null)
				throw new Exception("Kipu: Unknown thread error");			
			time = time + reload;
			kipuThread.time = time + kipuThread.reload;			
			kipuThread.execute();
			if (kipuThread.reload != 0)
				Kipu.this.insert(kipuThread);
			reload = this.peek().time - time;
			//System.out.println("Reload done");
	 	}
	}
}
