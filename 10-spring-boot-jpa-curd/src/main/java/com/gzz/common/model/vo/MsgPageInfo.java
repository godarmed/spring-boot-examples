package com.gzz.common.model.vo;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Classname MsgPageInfo
 * @Description TODO
 * @Date 2020/6/23 17:55
 * @Created by Zzy
 */
public class MsgPageInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer page;
    private Integer size;
    private Long total;
    private Integer numberOfElements;

    public static MsgPageInfo loadFromPageable(Page<?> page) {
        MsgPageInfo msgPageInfo = new MsgPageInfo();
        msgPageInfo.setNumberOfElements(page.getNumberOfElements());
        msgPageInfo.setPage(page.getNumber());
        msgPageInfo.setSize(page.getSize());
        msgPageInfo.setTotal(page.getTotalElements());
        return msgPageInfo;
    }

    public MsgPageInfo() {
    }

    public Integer getPage() {
        return this.page;
    }

    public Integer getSize() {
        return this.size;
    }

    public Long getTotal() {
        return this.total;
    }

    public Integer getNumberOfElements() {
        return this.numberOfElements;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof MsgPageInfo)) {
            return false;
        } else {
            MsgPageInfo other = (MsgPageInfo)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$page = this.getPage();
                    Object other$page = other.getPage();
                    if (this$page == null) {
                        if (other$page == null) {
                            break label59;
                        }
                    } else if (this$page.equals(other$page)) {
                        break label59;
                    }

                    return false;
                }

                Object this$size = this.getSize();
                Object other$size = other.getSize();
                if (this$size == null) {
                    if (other$size != null) {
                        return false;
                    }
                } else if (!this$size.equals(other$size)) {
                    return false;
                }

                Object this$total = this.getTotal();
                Object other$total = other.getTotal();
                if (this$total == null) {
                    if (other$total != null) {
                        return false;
                    }
                } else if (!this$total.equals(other$total)) {
                    return false;
                }

                Object this$numberOfElements = this.getNumberOfElements();
                Object other$numberOfElements = other.getNumberOfElements();
                if (this$numberOfElements == null) {
                    if (other$numberOfElements != null) {
                        return false;
                    }
                } else if (!this$numberOfElements.equals(other$numberOfElements)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof MsgPageInfo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, size, total, numberOfElements);
    }

    @Override
    public String toString() {
        return "MsgPageInfo(page=" + this.getPage() + ", size=" + this.getSize() + ", total=" + this.getTotal() + ", numberOfElements=" + this.getNumberOfElements() + ")";
    }
}
