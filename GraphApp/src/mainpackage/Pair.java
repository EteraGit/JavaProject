package mainpackage;

public class Pair<L,R> {
    private L left;
    private R right;
    
    public Pair(L l, R r){
        this.left = l;
        this.right = r;
    }
    
    public L getL(){ return left; }
    public R getR(){ return right; }
    public void setL(L l){ this.left = l; }
    public void setR(R r){ this.right = r; }
}