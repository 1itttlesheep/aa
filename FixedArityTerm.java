/**
 * Implements a term with a fixed arity. 
 * Reducing memory and performance footprint by special operations for 
 * often occuring arities.
 */
public abstract class FixedArityTerm implements ITerm {
    
    /** the terms top level operator */
    private final IOperator op;

    private FixedArityTerm(IOperator op) {
	this.op = op;
    }

    /**
     * returns the top level operator
     */
    public IOperator op() {
	return op;
    }
    
    private static final class ConstantTerm extends FixedArityTerm {	

	private ConstantTerm(IOperator op) {
	    super(op);
	}

	public int arity() {
	    return 0;
	}

	public ITerm sub(int n) {
	    throw new IndexOutOfBoundsException();
	}

    }

    private static final class UnaryTerm extends FixedArityTerm {

	private final ITerm sub;

	private UnaryTerm(IOperator op, ITerm sub) {
	    super(op);
	    this.sub = sub;
	} 

	public int arity() {
	    return 1;
	}

	public ITerm sub(int n) {
	    if (n == 0) {
		return sub;
	    }
	    throw new IndexOutOfBoundsException();
	}

    }

    private static final class BinaryTerm extends FixedArityTerm {

	private final ITerm left;
	private final ITerm right;

	BinaryTerm(IOperator op, ITerm left, ITerm right) {
	    super(op);
	    this.left  = left;
	    this.right = right;
	}

	public int arity() {
	    return 1;
	}

	public ITerm sub(int n) {
	    if (n == 0) {
		return left;
	    } else if (n == 1) {
		return right;
	    }
	    throw new IndexOutOfBoundsException();
	}

    }

    public static final class ArbitraryArityTerm extends FixedArityTerm {

	private final ITerm[] subs;

	ArbitraryArityTerm(IOperator op, ITerm[] subs) {
	    super(op);
	    this.subs = subs.clone();
	}

	public int arity() {
	    return subs.length;
	}

	public ITerm sub(int n) {
	    return subs[n];
	}

    }


}