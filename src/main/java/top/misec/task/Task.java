package top.misec.task;

import java.io.UnsupportedEncodingException;

/**
 * @author @Kurenai
 * @since 2020-11-22 5:22
 */
public interface Task {

    /**
     * 任务实现
     */
    void run() throws UnsupportedEncodingException, InterruptedException;

    /**
     * 任务名
     *
     * @return taskName
     */
    String getName();

}