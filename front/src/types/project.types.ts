import { IconType } from "react-icons";

export type ProjectStackProps = {
    stack: string
};

export type ProjectStackListProps = {
    stackList: string[];
};

export type ProjectLinkProps = { 
    href: string; 
    icon: IconType; 
    text: string 
};