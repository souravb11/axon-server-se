delete
from PROCESSOR_LOAD_BALANCING
where (COMPONENT, CONTEXT, NAME) not in (
    select (min(COMPONENT), CONTEXT, NAME)
    from PROCESSOR_LOAD_BALANCING
    group by CONTEXT, NAME
    );

ALTER TABLE PROCESSOR_LOAD_BALANCING
    DROP PRIMARY KEY;

ALTER TABLE PROCESSOR_LOAD_BALANCING
    DROP COLUMN (
        COMPONENT
        );

ALTER TABLE PROCESSOR_LOAD_BALANCING
    ADD PRIMARY KEY (
                     CONTEXT, NAME
        );