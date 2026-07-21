export class constants {
    public static readonly customerDetails = {
        customer: "MS GLOBAL ALLIANZ",
        address: "Address Line 1, Address Line 2, City",
        yearOfEstablishment: 2000,
        directors: [
            { name: "John Doe", age: 45 },
            { name: "Jane Smith", age: 50 }
        ],
        subsidiaries: [
            "XYZ Ltd.",
            "ABC Holdings",
            "PQR Enterprises"
        ],
        otherBankers: [],
        facilitiesfromOtherBanks: [
            { name: "John Doe", facility: "test1", limit: "1000Mn", os: "100Mn", security: "test security" },
            { name: "Jane Smith", facility: "test12", limit: "2000Mn", os: "200Mn", security: "test security 2" }
        ],
        facilityTaken: [],
        securityGiven: [
            "Loan Agreement with XYZ Bank",
            "Mortgage on Property ABC",
            "Corporate Guarantee to PQR Finance"
        ],
        kaliptoRisk: 57.00,
        roa: 5.02,
        roapf: 4.65,
        crib: "No Irregular Advances",
        financials: "Financials audited",
        roaHistory: [
            { label: "Sector code/description", value: "07000", description: "INFORMATION TECHNOLOGY AND COMMUNICATION SERVICES" },
            { label: "Subsector code/description", value: "07001", description: "INFORMATION TECHNOLOGY - HARDWARE" },
            { label: "Existing Risk Grade", value: "C+" }
        ]

    }
    public static readonly commentsdetails = {
        comments: [
            { userID: "Test User 1", date: "2023-2-12", comments: "test comments 1", commentType: "tramnsfered" },
            { userID: "Test User 2", date: "2023-2-13", comments: "test comments 3", commentType: "tramnsfered by" },
            { userID: "Test User 3", date: "2023-2-14", comments: "test comments 4", commentType: "tramnsfered" },
            { userID: "Test User 4", date: "2023-2-15", comments: "test comments 5", commentType: "tramnsfered" },
            { userID: "Test User 5", date: "2023-2-16", comments: "test comments 6", commentType: "tramnsfered" },
            { userID: "Test User 6", date: "2023-2-17", comments: "test comments 7", commentType: "tramnsfered by" },
            { userID: "Test User 7", date: "2023-2-18", comments: "test comments 8", commentType: "tramnsfered by" },
        ]
    };

    public static readonly facility = {
        facilities: [
            { name: 'Facility 1 : TERM LOAN   LKR 0.25 Mn', description: ["Coratteral for all facilities", "Counter Indemnity of the company", "Overdraft Agreement for LKR 0.50 Mn", "Loan Agreement for LKR 0.250 Mn"], cashAmount: "12300.00" },
            { name: 'Facility 2 : OVERDRAFT   LKR 0.5 Mn', description: ["Coratteral for all facilities", "Counter Indemnity of the company", "Overdraft Agreement for LKR 0.50 Mn", "Loan Agreement for LKR 0.250 Mn"], cashAmount: "12300.00" },
            { name: 'Facility 3 : SHORT TERM LOAN   LKR 0.75 Mn', description: ["Coratteral for all facilities", "Counter Indemnity of the company", "Overdraft Agreement for LKR 0.50 Mn", "Loan Agreement for LKR 0.250 Mn"], cashAmount: "12300.00" },
            { name: 'Facility 4 : LETTERS OF GUARANTEE   LKR 0.25 Mn', description: ["Coratteral for all facilities", "Counter Indemnity of the company", "Overdraft Agreement for LKR 0.50 Mn", "Loan Agreement for LKR 0.250 Mn"], cashAmount: "12300.00" },
        ]
    }

    public static readonly upcSection = [
        { label: "Ownership", value: "ownership" },
        { label: "Financial Condition", value: "financial_condition" },
        { label: "Security/ Collateral", value: "security_collateral" },
        { label: "Dates of Last Three Inspection", value: "inspection_dates" },
        { label: "Management", value: "management" },
        { label: "Activities", value: "activities" }
    ];

    public static readonly upc = [
        {name:"Ownership", lastModDate:"Last Modified Date : 12/5/24"},
        {name:"Financial Condition", lastModDate:"Last Modified Date : 12/5/24"},
        {name:"Security/ Collateral", lastModDate:"Last Modified Date : 12/5/24"},
        {name:"Dates of Last Three Inspection", lastModDate:"Last Modified Date : 12/5/24"},
        {name:"Management", lastModDate:"Last Modified Date : 12/5/24"},
        {name:"Activities", lastModDate:"Last Modified Date : 12/5/24"},

    ]
}