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

public class IntAndTableVisitor implements IVisitor<IntAndTable> {
	private Table t;

	public IntAndTableVisitor(Table t) {
		this.t = t;
	}

	@Override
	public IntAndTable visit(Stm s) {
		// TODO Auto-generated method stub
		return s.accept(this);
	}

	@Override
	public IntAndTable visit(AssignStm s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntAndTable visit(CompoundStm s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntAndTable visit(PrintStm s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntAndTable visit(Exp e) {
		// TODO Auto-generated method stub
		return e.accept(this);
	}

	@Override
	public IntAndTable visit(EseqExp e) {
		// TODO Auto-generated method stub
		Interpreter a = new Interpreter(t);
		t = e.getStm().accept(a);
		IntAndTable aux = e.getExp().accept(this);

		t = aux.table;

		return aux;
	}

	@Override
	public IntAndTable visit(IdExp e) {
		// TODO Auto-generated method stub
		Table aux = t;

		while (aux != null){
			if(aux.id == e.getId()){
				return new IntAndTable(aux.value, t);
			}

			aux = aux.tail;
		}

		return null;
	}

	@Override
	public IntAndTable visit(NumExp e) {
		// TODO Auto-generated method stub
		return new IntAndTable(e.getNum(), t);
	}

	@Override
	public IntAndTable visit(OpExp e) {
		// TODO Auto-generated method stub

		IntAndTable returnTable = new IntAndTable(0, t);
		double left = e.getLeft().accept(this).result;
		double right = e.getRight().accept(this).result;

		if(e.getOper() == 1){
			returnTable.result = left + right;
		} else if(e.getOper() == 2) {
			returnTable.result = left - right;
		} else if(e.getOper() == 3) {
			returnTable.result = left * right;
		} else if(e.getOper() == 4) {
			returnTable.result = left / right;
		}

		return returnTable;
	}

	@Override
	public IntAndTable visit(ExpList el) {
		// TODO Auto-generated method stub
		return el.accept(this);
	}

	@Override
	public IntAndTable visit(PairExpList el) {
		// TODO Auto-generated method stub
		IntAndTable aux = el.getHead().accept(this);

		Interpreter i = new Interpreter(t);
		el.getTail().accept(i);
		return aux;
	}

	@Override
	public IntAndTable visit(LastExpList el) {
		// TODO Auto-generated method stub
		return el.getHead().accept(this);
	}


}
