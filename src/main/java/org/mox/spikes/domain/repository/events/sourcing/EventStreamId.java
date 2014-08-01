package org.mox.spikes.domain.repository.events.sourcing;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public final class EventStreamId {

    private String streamName;

    private int streamVersion;

    public EventStreamId(final String aStreamName) {

        this(aStreamName, 1);
    }

    //always use streamversion = 1. why?
    @Deprecated
    public EventStreamId(final String aStreamName, final int aStreamVersion) {

        super();

        this.setStreamName(aStreamName);
        this.setStreamVersion(aStreamVersion);
    }

    public EventStreamId(final String aStreamNameSegment1, final String aStreamNameSegment2) {

        this(aStreamNameSegment1, aStreamNameSegment2, 1);
    }

    @Deprecated
    public EventStreamId(String aStreamNameSegment1, String aStreamNameSegment2,
                         int aStreamVersion) {

        this(aStreamNameSegment1 + ":" + aStreamNameSegment2, aStreamVersion);
    }

    public String streamName() {

        return this.streamName;
    }

    public int streamVersion() {

        return this.streamVersion;
    }

    public EventStreamId withStreamVersion(int aStreamVersion) {

        return new EventStreamId(this.streamName(), aStreamVersion);
    }

    private void setStreamName(String aStreamName) {

        this.streamName = aStreamName;
    }

    private void setStreamVersion(int aStreamVersion) {

        this.streamVersion = aStreamVersion;
    }

}
