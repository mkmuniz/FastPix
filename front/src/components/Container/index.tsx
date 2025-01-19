import React from "react";
import { ContainerProps } from "../../types/components.types";

export default function Container({ children }: ContainerProps) {
    return (
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
            {children}
        </div>
    );
}