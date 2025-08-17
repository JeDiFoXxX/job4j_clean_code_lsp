package ru.job4j.ood.isp.menu;

import java.util.*;

public class SimpleMenu implements Menu {
    private final List<MenuItem> rootElements = new ArrayList<>();

    @Override
    public boolean add(String parentName, String childName, ActionDelegate actionDelegate) {
        boolean rsl = false;
        boolean equals = Objects.equals(parentName, ROOT);
        if (equals) {
            rootElements.add(new SimpleMenuItem(childName, actionDelegate));
            rsl = true;
        }
        if (!equals) {
            findItem(parentName).ifPresent(itemInfo ->
                    itemInfo.menuItem.getChildren().add(new SimpleMenuItem(childName, actionDelegate))
            );
            rsl = true;
        }
        return rsl;
    }

    @Override
    public Optional<MenuItemInfo> select(String itemName) {
        return findItem(itemName)
                .map(itemInfo ->
                        new MenuItemInfo(itemInfo.menuItem, itemInfo.number)
                );
    }

    @Override
    public Iterator<MenuItemInfo> iterator() {
        Iterator<ItemInfo> iteratorItemInfo = new DFSIterator();
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return iteratorItemInfo.hasNext();
            }

            @Override
            public MenuItemInfo next() {
                ItemInfo itemInfo = iteratorItemInfo.next();
                return new MenuItemInfo(itemInfo.menuItem, itemInfo.number);
            }
        };
    }

    private Optional<ItemInfo> findItem(String name) {
        Optional<ItemInfo> optional = Optional.empty();
        Iterator<ItemInfo> iteratorItemInfo = new DFSIterator();
        while (iteratorItemInfo.hasNext()) {
            ItemInfo itemInfo = iteratorItemInfo.next();
            if (name.equals(itemInfo.menuItem.getName())) {
                optional = Optional.of(itemInfo);
                break;
            }
        }
        return optional;
    }

    private static class SimpleMenuItem implements MenuItem {
        private String name;
        private List<MenuItem> children = new ArrayList<>();
        private ActionDelegate actionDelegate;

        public SimpleMenuItem(String name, ActionDelegate actionDelegate) {
            this.name = name;
            this.actionDelegate = actionDelegate;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<MenuItem> getChildren() {
            return children;
        }

        @Override
        public ActionDelegate getActionDelegate() {
            return actionDelegate;
        }
    }

    private class DFSIterator implements Iterator<ItemInfo> {
        private final Deque<MenuItem> stack = new LinkedList<>();

        private final Deque<String> numbers = new LinkedList<>();

        DFSIterator() {
            int number = 1;
            for (MenuItem item : rootElements) {
                stack.addLast(item);
                numbers.addLast(String.valueOf(number++).concat("."));
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public ItemInfo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            MenuItem current = stack.removeFirst();
            String lastNumber = numbers.removeFirst();
            List<MenuItem> children = current.getChildren();
            int currentNumber = children.size();
            for (var i = children.listIterator(children.size()); i.hasPrevious();) {
                stack.addFirst(i.previous());
                numbers.addFirst(lastNumber.concat(String.valueOf(currentNumber--)).concat("."));
            }
            return new ItemInfo(current, lastNumber);
        }
    }

    private static class ItemInfo {
        private final MenuItem menuItem;
        private final String number;

        public ItemInfo(MenuItem menuItem, String number) {
            this.menuItem = menuItem;
            this.number = number;
        }
    }
}