package CoursePlanner.Model;

import CoursePlanner.AllApiDtoClasses.ApiWatcherCreateDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class WatcherList {
    private final AtomicLong watcherIdCounter = new AtomicLong(1);
    List<Watcher> watcherList;

    public WatcherList() {
        watcherList = new ArrayList<>();
    }

    public static WatcherList makeWatcherList() {
        return new WatcherList();
    }

    public Watcher getWatcher(long id) {
        for (Watcher watcher : watcherList) {
            if (watcherIdCounter.get() == id) {
                return watcher;
            }
        }
        return null;
    }

    public Watcher getWatcher(String department, String catalogNumber) {
        for (Watcher watcher : watcherList) {
            if (watcher.getDepartmentName().equals(department) && watcher.getCourseNumber().equals(catalogNumber)) {
                return watcher;
            }
        }
        return null;
    }

    public List<Watcher> getWatchers() {
        return watcherList;
    }

    public void addWatcher(CourseList courseList, ApiWatcherCreateDTO newWatcher) {
        Watcher watcher = new Watcher(watcherIdCounter.getAndIncrement(), courseList, newWatcher);
        watcherList.add(watcher);
    }

    public boolean deleteWatcher(long id) {
        for (Watcher watcher : watcherList) {
            if (watcherIdCounter.get() == id) {
                watcherList.remove(watcher);
                return true;
            }
        }
        return false;
    }
}