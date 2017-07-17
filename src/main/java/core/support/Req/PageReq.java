package core.support.Req;

/**
 * Created by ssc on 2017/7/17 0017.
 */
public final class PageReq {
    private int size;

    private int page;

    public PageReq() {
    }

    public PageReq(int page, int size) {
        super();
        this.size = size;
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
