package br.ufpe.cin.if688.visitor;

import br.ufpe.cin.if688.ast.AssignStm;
import br.ufpe.cin.if688.ast.CompoundStm;
import br.ufpe.cin.if688.ast.EseqExp;
import br.ufpe.cin.if688.ast.Exp;
import br.ufpe.cin.if688.ast.ExpList;
import br.ufpe.cin.if688.ast.IdExp;
import br.ufpe.cin.if688.ast.LastExpList;
import br.ufpe.cin.if688.ast.NumExp;
import br.ufpe.cin.if688.ast.OpExp;
import br.ufpe.cin.if688.ast.PairExpList;
import br.ufpe.cin.if688.ast.PrintStm;
import br.ufpe.cin.if688.ast.Stm;
import br.ufpe.cin.if688.symboltable.IntAndTable;
import br.ufpe.cin.if688.symboltable.Table;

public class Interpreter implements IVisitor<Table> {
	
	//a=8;b=80;a=7;
	// a->7 ==> b->80 ==> a->8 ==> NIL
	private Table t;
	
	public Interpreter(Table t) {
		this.t = t;
	}

	@Override
	public Table visit(Stm s) {
		// TODO Auto-generated method stub
		return t = s.accept(this);
	}

	@Override
	public Table visit(AssignStm s) {
		// TODO Auto-generated method stub
		t = s.getExp().accept(this);
		IntAndTableVisitor a = new IntAndTableVisitor(t);

		return t = new Table(s.getId(), s.getExp().accept(a).result, t);
	}

	@Override
	public Table visit(CompoundStm s) {
		// TODO Auto-generated method stub
		t = s.getStm1().accept(this);
		t = s.getStm2().accept(this);
		return t;
	}

	@Override
	public Table visit(PrintStm s) {
		// TODO Auto-generated method stub
		IntAndTableVisitor a = new IntAndTableVisitor(t);

		if(s.getExps() instanceof PairExpList){
			ExpList myPair = (PairExpList) s.getExps();
			while (myPair != null) {
				IntAndTable aux = myPair.accept(a);
				System.out.println(aux.result);
				myPair = myPair.getTail();
			}
		} else {
			IntAndTable aux = s.getExps().accept(a);
			System.out.println(aux.result);
		}

		return t = aux.table;
	}

	@Override
	public Table visit(Exp e) {
		// TODO Auto-generated method stub
		return t = e.accept(this);
	}

	@Override
	public Table visit(EseqExp e) {
		// TODO Auto-generated method stub
		t = e.getStm().accept(this);
		t = e.getExp().accept(this);
		return t;
	}

	@Override
	public Table visit(IdExp e) {
		// TODO Auto-generated method stub
		return t;
	}

	@Override
	public Table visit(NumExp e) {
		// TODO Auto-generated method stub
//		System.out.println(e.getNum());
		return t;
	}

	@Override
	public Table visit(OpExp e) {
		// TODO Auto-generated method stub
		IntAndTableVisitor a = new IntAndTableVisitor(t);
		double aux = e.accept(a).result;
//		System.out.println(aux);

		return t;
	}

	@Override
	public Table visit(ExpList el) {
		// TODO Auto-generated method stub
		return t = el.accept(this);
	}

	@Override
	public Table visit(PairExpList el) {
		// TODO Auto-generated method stub
		Table table = el.getHead().accept(this);
		return t = new Table(table.id, table.value, el.getTail().accept(this));
	}

	@Override
	public Table visit(LastExpList el) {
		// TODO Auto-generated method stub
		return t = el.getHead().accept(this);
	}


}
