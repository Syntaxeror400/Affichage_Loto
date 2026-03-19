// Copyright (C) 2026 Remi Lemaire

package content.cstm;

import java.util.ArrayList;
import java.util.Iterator;

import content.visuals.LotoLabel;

/**
 * The {@code Grid} class contains the table of {@code LotoLabel} and all the methods necessary to
 * modify the grid.
 * 
 * @author Remi Lemaire
 *
 * @see GUI
 * @see LotoLabel
 */
public class Grid{
	LotoLabel[][] labels;
	ArrayList<Integer> numbers,undone, checked,unchecked, adminDel,adminDelCheck,adminAdd,adminAddAt;
	int adminSum;
	
	public Grid(){
		numbers = new ArrayList<Integer>(90);
		undone = new ArrayList<Integer>(90);
		checked = new ArrayList<Integer>(90);
		unchecked = new ArrayList<Integer>(90);
		adminDel = new ArrayList<Integer>(90);
		adminDelCheck  = new ArrayList<Integer>(90);
		adminAdd  = new ArrayList<Integer>(90);
		adminAddAt = new ArrayList<Integer>(90);
		
		labels = new LotoLabel[9][10];
		for(int i=0;i<9;i++)
			for(int k=0;k<10;k++)
				labels[i][k] = new LotoLabel(String.valueOf(10*i+k));
		labels[0][0].setValue("90");
	}
	
	public void showAll(){
		int size=numbers.size();
		undone.clear();
		for(int i=0;i<size;i++)
			undone.add(numbers.get(size-1-i));
		numbers.clear();
		
		for(int i=0;i<9;i++)
			for(int k=0;k<10;k++){
				numbers.add(10*i+k+1);
				labels[i][k].setState(2);
			}
		labels[0][0].setState(1);
	}
	public int checkAll(){
		int size=checked.size(), ret=0;
		unchecked.clear();
		for(int i=0;i<size;i++)
			unchecked.add(checked.get(size-1-i));
		checked.clear();

		for(int i=0;i<9;i++)
			for(int k=0;k<10;k++){
				checked.add(10*i+k+1);
				labels[i][k].setState(3);
				if(labels[i][k].isShown())
					ret++;
			}
		return ret;
	}
	
	public void resetAll(){
		for(LotoLabel[] ll:labels)
			for(LotoLabel l:ll)
				l.setState(0);
		numbers.clear();
		undone.clear();
		checked.clear();
		unchecked.clear();
	}
	public void resetCheck(){
		for(Iterator<Integer> it=checked.iterator();it.hasNext();)
			uncheckLabel(it.next());
		checked.clear();
		unchecked.clear();		
	}
	public void clearUndo(){
		undone.clear();
	}
	public void clearUndoCheck(){
		unchecked.clear();
	}
	
	public void undo(){
		int size=numbers.size();
		if(size>0){
			int n=numbers.remove(--size);
			undone.add(n);
			setLabelState(n, 0);
			if(size>0)
				setLabelState(numbers.get(size-1), 1);
		}
	}
	public void undoAll(){
		int size=numbers.size();
		
		undone.clear();
		for(int i=0; i<size;i++)
			undone.add(numbers.get(size-i-1));
		numbers.clear();
		
		for(LotoLabel[] ll:labels)
			for(LotoLabel l:ll)
				l.setState(0);
	}
	public boolean uncheck(){
		int size=checked.size();
		if(size>0){
			int n=checked.remove(size-1);
			unchecked.add(n);
			return uncheckLabel(n);
		}
		return false;
	}
	public void uncheckAll(){
		int sizeC=checked.size(), sizeN=numbers.size();
		
		unchecked.clear();
		for(int i=0;i<sizeC;i++)
			unchecked.add(checked.get(sizeC-i-1));
		checked.clear();
		for(LotoLabel[] ll:labels)
			for(LotoLabel l:ll)
				if(l.isShown())
					l.setState(2);
				else
					l.setState(0);
		if(sizeN>0)
			setLabelState(numbers.get(sizeN-1), 1);
	}
	
	public void redo(){
		int sizeU=undone.size(), sizeN=numbers.size();
		if(sizeU>0){
			if(sizeN>0)
				setLabelState(numbers.get(sizeN-1),2);
			int n=undone.remove(sizeU-1);
			numbers.add(n);
			setLabelState(n,1);
		}
	}
	public void redoAll(){
		int size=undone.size();
		
		numbers.clear();
		for(LotoLabel[] ll:labels)
			for(LotoLabel l:ll)
				l.setState(0);
		
		for(int i=0; i<size;i++){
			int n=undone.get(size-i-1);
			numbers.add(n);
			setLabelState(n,2);
		}
		if(size>0)
			setLabelState(numbers.get(size-1), 1);
		undone.clear();
	}
	public boolean recheck(){
		int size=unchecked.size();
		if(size>0){
			int n=unchecked.remove(size-1);
			checked.add(n);
			setLabelState(n,3);
			return getLabel(n).isShown();
		}
		return false;
	}
	public int recheckAll(){
		int size=unchecked.size(),ret=0;
		
		checked.clear();
		for(int i=0;i<90;i++)
			uncheckLabel(i);
		
		for(int i=0; i<size;i++){
			int n=unchecked.get(size-i-1);
			checked.add(n);
			setLabelState(n,3);
			if(getLabel(n).isShown())
				ret++;
		}
		unchecked.clear();
		return ret;
	}
	
	public boolean add(int number){
		if(numbers.contains(number) || number<=0 || number>90)
			return false;
		else{
			int size = numbers.size();
			
			resetCheck();
			undone.clear();
			if(size!=0)
				setLabelState(numbers.get(size-1), 2);	
			numbers.add(number);
			setLabelState(number, 1);
			
			return true;
		}
	}
	public boolean check(int number){
		checked.add(number);
		unchecked.clear();
		
		if(number==90){
			labels[0][0].setState(3);
			return labels[0][0].isShown();
		}else{
			labels[number/10][number%10].setState(3);
			return labels[number/10][number%10].isShown();
		}
	}
	
	public void bootAdmin(){
		adminDel.clear();
		adminDelCheck.clear();
		adminAdd.clear();
		adminAddAt.clear();
		adminSum=0;
	}
	public void adminAdd(int number, int place){
		if(number>0 && number <=90 && !numbers.contains(number) && !adminAdd.contains(number)){
			if(!adminDel.remove((Object)number)){
				if(place == numbers.size()+adminSum){
					adminAdd.add(number);
					adminAddAt.add(-1);
				}else{
					adminAdd.add(number);
					adminAddAt.add(place);
				}
				adminSum++;
			}
		}
	}
	public void adminDel(int number){
		if(number>0 && number <=90 && numbers.contains(number) && !adminDel.contains(number)){
			if(adminAdd.contains(number)){
				int i=adminAdd.indexOf(number);
				adminAdd.remove(i);
				adminAddAt.remove(i);
			}else
				adminDel.add(number);
		}
	}
	public void adminDelCheck(int number){
		if(number>0 && number <=90 && checked.contains(number) && !adminDel.contains(number) && !adminDelCheck.contains(number))
			adminDelCheck.add(number);
	}
	public void confirmAdmin(){
		for(Iterator<Integer>it=adminDelCheck.iterator();it.hasNext();){
			int n=it.next();
			checked.remove((Object)n);
			uncheckLabel(n);
		}
		for(int i=0;i<adminSum;i++)
			if(adminAddAt.get(i)==-1){
				int size=numbers.size();
				if(size>0)
					smartSetState(numbers.get(size-1),2);
				numbers.add(adminAdd.get(i));
				smartSetState(adminAdd.get(i),1);
			}else{
				numbers.add(adminAddAt.get(i),adminAdd.get(i));
				smartSetState(adminAdd.get(i),2);
			}
		for(Iterator<Integer> it=adminDel.iterator();it.hasNext();){
			int n=it.next();
			numbers.remove((Object)n);
			smartSetState(n,0);
		}
	}
	
	public int numberSize(){
		return numbers.size();
	}
	public int undoneSize(){
		return undone.size();
	}
	public int checkedSize(){
		return checked.size();
	}
	public int uncheckedSize(){
		return unchecked.size();
	}
	public int adminSize(){
		return numbers.size()+adminSum;
	}
	
	public boolean isAlreadyChecked(int number){
		return checked.contains(number) || number>90 || number<=0;
	}
	public ArrayList<Integer> getNumbers(){
		return numbers;
	}
	public LotoLabel getLabel(int row,int col){
		return labels[row][col];
	}
	
	public void repaint(){
		for(LotoLabel[] ll:labels)
			for(LotoLabel l:ll)
				l.repaint();
	}
	
	private LotoLabel getLabel(int number){
		if(number==90)
			return labels[0][0];
		else
			return labels[number/10][number%10];
	}
	private void setLabelState(int number,int state){
		getLabel(number).setState(state);
	}
	private boolean uncheckLabel(int number){
		LotoLabel l = getLabel(number);
		
		if(l.isShown()){
			if(number==numbers.get(numbers.size()-1))
				l.setState(1);
			else
				l.setState(2);
			return true;
		}else{
			l.setState(0);
			return false;
		}
	}
	private void smartSetState(int number, int state){
		LotoLabel l = getLabel(number);
		
		l.setState(state);
		if(checked.contains(number))
			l.setState(3);
	}
}