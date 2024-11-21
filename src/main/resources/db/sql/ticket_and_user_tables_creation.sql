CREATE TABLE IF NOT EXISTS ticket (
    id UUID PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    price_in_usd DECIMAL(10, 2),
    concert_hall_name VARCHAR(11), -- Specific to ConcertTicket
    event_code SMALLINT, -- Specific to ConcertTicket
    backpack_weight_in_kg FLOAT, -- Specific to ConcertTicket
    stadium_sector CHARACTER, -- Specific to ConcertTicket
    time TIMESTAMP, -- Specific to ConcertTicket
    is_promo BOOLEAN, -- Specific to ConcertTicket
    ticket_class VARCHAR(50), -- Specific to BusTicket
    duration VARCHAR(50), -- Specific to BusTicket
    start_date DATE, -- Specific to BusTicket
    CHECK (type IN ('CONCERT', 'BUS'))
);

CREATE TABLE IF NOT EXISTS "user" (
    id UUID PRIMARY KEY,
    role VARCHAR(50) NOT NULL,
    ticket_id UUID UNIQUE REFERENCES ticket(id), -- Specific to Client
    CHECK (role IN ('CLIENT', 'ADMIN'))
);