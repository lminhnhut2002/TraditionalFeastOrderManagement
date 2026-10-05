package business;

/**
 * Chua cac mo ta ve nhung hanh vi ma lop tuong ung can phai trien khai
 * theo yeu cau ve thiet ke cua chuong trinh [Ap dung: Customers, Orders]
 * @param <T> Kieu du lieu se su dung de trien khai trong tinh huong cu the
 */
public interface Workable<T> {
    public void addNew(T x);
   public void update(T x);
   public T searchById(String id);
   public void showAll();
}
