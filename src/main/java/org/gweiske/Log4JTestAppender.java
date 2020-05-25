package org.gweiske;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.ErrorHandler;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.DefaultErrorHandler;
import org.apache.logging.log4j.core.config.Configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Log4JTestAppender implements Appender, AutoCloseable
{
    private ErrorHandler errorHandler = new DefaultErrorHandler(this);
    private boolean isStarted = false;
    private List<LogEvent> logEvents;
    private State lifecycleState;

    public boolean validate(String ... expectedStrings)
    {
        for (LogEvent event : logEvents)
        {
            boolean status = true;
            for (String expectedString : expectedStrings)
            {
                status &= event.getMessage().toString().contains(expectedString);
            }
            if (status)
            {
                return true;
            }
        }
        return false;
    }

    public Log4JTestAppender setup()
    {
        initialize();
        start();
        return this;
    }

    @Override
    public void append(LogEvent event)
    {
        logEvents.add(event);
    }

    @Override
    public String getName()
    {
        return "TestAppender";
    }

    @Override
    public Layout<? extends Serializable> getLayout()
    {
        return null;
    }

    @Override
    public boolean ignoreExceptions()
    {
        return false;
    }

    @Override
    public ErrorHandler getHandler()
    {
        return errorHandler;
    }

    @Override
    public void setHandler(ErrorHandler handler)
    {
        errorHandler = handler;
    }

    @Override
    public State getState()
    {
        return lifecycleState;
    }

    @Override
    public void initialize()
    {
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(true);
        final Configuration config = ctx.getConfiguration();
        config.addAppender(this);  // this is optional
        ctx.getRootLogger().addAppender(this);
        ctx.updateLoggers();
        lifecycleState = State.INITIALIZED;

        logEvents = new ArrayList<>();
    }

    @Override
    public void start()
    {
        logEvents.clear();
        isStarted = true;
        lifecycleState = State.STARTED;
    }

    @Override
    public void stop()
    {
        isStarted = false;
        lifecycleState = State.STOPPED;
    }

    @Override
    public boolean isStarted()
    {
        return isStarted;
    }

    @Override
    public boolean isStopped()
    {
        return !isStarted;
    }

    @Override
    public void close() throws Exception
    {
        isStarted = false;
        lifecycleState = State.STOPPED;
    }
}
