package com.jinho.job;

import java.util.List;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

public class CustomItemStreamReader implements ItemStreamReader<Integer> {

    private final List<Integer> items;
    private int index = 0;
    private boolean restart = false;

    public CustomItemStreamReader(final List<Integer> items) {
        this.items = items;
    }

    @Override
    public Integer read() {
        Integer item = null;
        if (index < items.size()) {
            item = items.get(index);
            index++;
        }

        if (index == 6 && !restart) {
            throw new RuntimeException("restart is required");
        }

        return item;
    }

    @Override
    public void open(final ExecutionContext executionContext) throws ItemStreamException {
        if (executionContext.containsKey("index")) {
            this.index = executionContext.getInt("index");
            this.restart = true;
        } else {
            this.index = 0;
            executionContext.put("index", index);
        }
    }

    @Override
    public void update(final ExecutionContext executionContext) throws ItemStreamException {
        executionContext.put("index", index);
    }

    @Override
    public void close() throws ItemStreamException {

    }
}
