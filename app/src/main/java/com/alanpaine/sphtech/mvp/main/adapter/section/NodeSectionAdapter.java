package com.alanpaine.sphtech.mvp.main.adapter.section;

import com.alanpaine.sphtech.bean.section.ItemNode;
import com.alanpaine.sphtech.bean.section.RootNode;
import com.alanpaine.sphtech.mvp.main.adapter.section.provider.RootNodeProvider;
import com.alanpaine.sphtech.mvp.main.adapter.section.provider.SecondNodeProvider;
import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NodeSectionAdapter extends BaseNodeAdapter {

    public NodeSectionAdapter() {
        super();
        addFullSpanNodeProvider(new RootNodeProvider());
        addNodeProvider(new SecondNodeProvider());
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> data, int position) {
        BaseNode node = data.get(position);
        if (node instanceof RootNode) {
            return 0;
        } else if (node instanceof ItemNode) {
            return 1;
        }
        return -1;
    }
}
