create table Contract (
    missionID bigint,
    agentID bigint,
    budget bigint,
    startTime date,
    endTime date,
    primary key (missionID, AgentID)
);