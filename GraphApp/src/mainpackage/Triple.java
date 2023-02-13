package mainpackage;

public class Triple<L,M,R> {
    private L left;
    private M middle;
    private R right;
    
    public Triple(L l, M m,  R r){
        this.left = l;
        this.middle = m;
        this.right = r;
    }
    
    public L getL(){ return left; }
    public M getM() { return middle; }
    public R getR(){ return right; }
    public void setL(L l){ this.left = l; }
    public void setM(M m){ this.middle = m; }
    public void setR(R r){ this.right = r; }
}
