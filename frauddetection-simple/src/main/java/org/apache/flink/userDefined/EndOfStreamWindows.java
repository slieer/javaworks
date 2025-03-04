package org.apache.flink.userDefined;

import org.apache.flink.annotation.Internal;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.WindowAssigner;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.util.Collection;
import java.util.Collections;

public class EndOfStreamWindows extends WindowAssigner<Object, TimeWindow> {
    private static final long serialVersionUID = 1L;

    private static final EndOfStreamWindows INSTANCE = new EndOfStreamWindows();

    private static final TimeWindow TIME_WINDOW_INSTANCE =
            new TimeWindow(Long.MIN_VALUE, Long.MAX_VALUE);

    private EndOfStreamWindows() {}

    public static EndOfStreamWindows get() {
        return INSTANCE;
    }

    @Override
    public Collection<TimeWindow> assignWindows(
            Object element, long timestamp, WindowAssignerContext context) {
        return Collections.singletonList(TIME_WINDOW_INSTANCE);
    }

    @Override
    public Trigger<Object, TimeWindow> getDefaultTrigger(StreamExecutionEnvironment env) {
        return new EndOfStreamTrigger();
    }

    @Override
    public String toString() {
        return "EndOfStreamWindows()";
    }

    @Override
    public TypeSerializer<TimeWindow> getWindowSerializer(ExecutionConfig executionConfig) {
        return new TimeWindow.Serializer();
    }

    @Override
    public boolean isEventTime() {
        return true;
    }

    @Internal
    public static class EndOfStreamTrigger extends Trigger<Object, TimeWindow> {
        @Override
        public TriggerResult onElement(
                Object element, long timestamp, TimeWindow window, TriggerContext ctx)
                throws Exception {
            return TriggerResult.CONTINUE;
        }

        @Override
        public TriggerResult onEventTime(long time, TimeWindow window, TriggerContext ctx) {
            return time == window.maxTimestamp() ? TriggerResult.FIRE : TriggerResult.CONTINUE;
        }

        @Override
        public void clear(TimeWindow window, TriggerContext ctx) throws Exception {}

        @Override
        public TriggerResult onProcessingTime(long time, TimeWindow window, TriggerContext ctx) {
            return TriggerResult.CONTINUE;
        }
    }
}