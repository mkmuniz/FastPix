"use client";

import React, { useState } from "react";

import Section from "../Section";
import Container from "../Container";

import PixForm from "./PixForm";
import QRCodeDisplay from "./QRCodeDisplay";

export default function Pix() {
    const [qrCodeData, setQrCodeData] = useState<{text: string; image: string} | null>(null);

    return (
        <Section>
            <Container>
                <PixForm onQrCodeGenerated={setQrCodeData} />
                <QRCodeDisplay qrCodeData={qrCodeData} />
            </Container>
        </Section>
    );
}