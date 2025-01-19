import React from "react";
import { SectionProps } from "../../types/components.types";

export default function Section({ children }: SectionProps) {
    return <>
        <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
            {children}
        </section>
    </>
}
