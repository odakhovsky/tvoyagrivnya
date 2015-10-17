package com.tvoyagryvnia.bean.category;


import java.util.List;
import java.util.stream.Collectors;

public class CategoryNode {

    private String text;

    private CategoryBean bean;

    private boolean selectable;

    private boolean base;

    private NodeState state = new NodeState();

    private List<CategoryNode> nodes;

    private Object[] tags;

    public CategoryNode(CategoryBean categoryBean) {
        bean = categoryBean;
        text = categoryBean.getName();
        selectable = true;
        base = (0 == categoryBean.getParent());
        nodes = categoryBean.getChildrens().stream().filter(CategoryBean::getActive).map(CategoryNode::new).collect(Collectors.toList());
        tags = new Object[]{nodes.size()};
        if (categoryBean.getMainCategory() > 0) {
            state.removed = false;
            state.edited = false;
        }else{
            state.removed = true;
            state.edited = true;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CategoryBean getBean() {
        return bean;
    }

    public void setBean(CategoryBean bean) {
        this.bean = bean;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isBase() {
        return base;
    }

    public void setBase(boolean base) {
        this.base = base;
    }

    public NodeState getState() {
        return state;
    }

    public void setState(NodeState state) {
        this.state = state;
    }

    public List<CategoryNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<CategoryNode> nodes) {
        this.nodes = nodes;
    }

    public Object[] getTags() {
        return tags;
    }

    public void setTags(Object[] tags) {
        this.tags = tags;
    }
}
